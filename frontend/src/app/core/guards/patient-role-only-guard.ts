import {CanActivateFn, Router} from '@angular/router';
import {AuthService} from '../../services/auth-service';
import {inject} from '@angular/core';

export const patientRoleOnlyGuard: CanActivateFn = (_, state) => {
  const authService = inject(AuthService);
  const router = inject(Router);

  if (!authService.isLoggedIn()) {
    return router.createUrlTree(['/login'], {queryParams: {returnUrl: state.url}});
  }
  const logged_user_role = authService.getRole()
  if (logged_user_role && logged_user_role === 'PATIENT') return true;
  return router.createUrlTree([''])
};
