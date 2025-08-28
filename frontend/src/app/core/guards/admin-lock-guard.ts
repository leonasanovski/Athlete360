import { CanActivateFn, CanActivateChildFn, Router } from '@angular/router';
import { inject } from '@angular/core';
import { AuthService } from '../../services/auth-service';

export const adminLockGuard: CanActivateFn = (_route, state) => {
  const auth = inject(AuthService);
  const router = inject(Router);

  if (auth.isLoggedIn() && auth.getRole() === 'ADMIN' && !state.url.startsWith('/admin')) {
    return router.createUrlTree(['/admin']);
  }
  return true;
};

export const adminLockChildGuard: CanActivateChildFn = (_route, state) =>
  adminLockGuard(_route, state);
