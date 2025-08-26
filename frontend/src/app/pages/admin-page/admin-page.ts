import {Component, inject, OnInit} from '@angular/core';
import { AppUserDTO } from '../../models/dto/AppUserDTO';
import {AdminService} from '../../services/admin-service';
import {UserRole} from '../../models/types/UserRole';
import {debounceTime, distinctUntilChanged, Subject, switchMap} from 'rxjs';
import {DatePipe} from '@angular/common';
import {FormsModule} from '@angular/forms';
import {AuthService} from '../../services/auth-service';
import {Router} from '@angular/router';

@Component({
  selector: 'admin-page',
  imports: [
    DatePipe,
    FormsModule
  ],
  templateUrl: './admin-page.html',
  styleUrl: './admin-page.css'
})
export class AdminPage implements OnInit {
  authService = inject(AuthService);
  router = inject(Router);

  users: AppUserDTO[] = []
  page = 0
  size = 20
  totalElements = 0
  totalPages = 0
  sort = 'createdAt,desc'
  embgSearch = ''
  loading = false

  query$ = new Subject<string>();

  adminService = inject(AdminService)

  ngOnInit(): void {
    this.load();

    this.query$.pipe(
      debounceTime(400),
      distinctUntilChanged(),
      switchMap(query => {
        this.loading = true
        if(query) {
          return this.adminService.getPending(this.page, this.size, this.sort, query.trim() || undefined)
        } else {
          return this.adminService.getPending(this.page, this.size, this.sort, this.embgSearch.trim() || undefined)
        }
      })
    ).subscribe(res => {
      this.users = res.content
      this.totalElements = res.totalElements
      this.totalPages = res.totalPages
      this.page = res.number
      this.loading = false
    })
  }

  load() {
    this.loading = true
    this.adminService.getPending(this.page, this.size, this.sort, this.embgSearch.trim() || undefined)
      .subscribe(res => {
        this.users = res.content
        this.totalElements = res.totalElements
        this.totalPages = res.totalPages
        this.page = res.number
        this.loading = false
      })
  }

  onSearch(query: string) {
    this.page = 0;
    this.query$.next(query);
  }

  toggleSort() {
    if (this.sort.endsWith('desc')) this.sort = 'createdAt,asc'
    else this.sort = 'createdAt,desc'
    this.load()
  }

  onRoleChange(user: AppUserDTO, newRole: UserRole) {
    if (user.userId == null) return
    this.adminService.updateUserRole(user.userId, newRole).subscribe(updated => {
      user.role = updated.role
    })
  }

  confirmAndDelete(user: AppUserDTO) {
    if (!user.userId) return
    if (!confirm(`Delete user ${user.firstName} ${user.lastName} (${user.embg})?`)) return
    this.adminService.deleteUser(user.userId).subscribe(() => {
      this.users = this.users.filter(u => u.userId !== user.userId)
    })
  }

  goToPage(p: number) {
    if (p < 0 || p >= this.totalPages) return
    this.page = p
    this.load()
  }

  onLogout() {
    this.authService.logout();
    this.router.navigate(['/login']);
  }
}
