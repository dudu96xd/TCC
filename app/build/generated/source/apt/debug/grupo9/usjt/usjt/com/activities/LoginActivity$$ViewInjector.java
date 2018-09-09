// Generated code from Butter Knife. Do not modify!
package grupo9.usjt.usjt.com.activities;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.Injector;

public class LoginActivity$$ViewInjector<T extends grupo9.usjt.usjt.com.activities.LoginActivity> implements Injector<T> {
  @Override public void inject(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131230828, "field '_emailText'");
    target._emailText = finder.castView(view, 2131230828, "field '_emailText'");
    view = finder.findRequiredView(source, 2131230830, "field '_passwordText'");
    target._passwordText = finder.castView(view, 2131230830, "field '_passwordText'");
    view = finder.findRequiredView(source, 2131230762, "field '_loginButton'");
    target._loginButton = finder.castView(view, 2131230762, "field '_loginButton'");
    view = finder.findRequiredView(source, 2131230840, "field '_signupLink'");
    target._signupLink = finder.castView(view, 2131230840, "field '_signupLink'");
    view = finder.findRequiredView(source, 2131230843, "field '_fbLogin'");
    target._fbLogin = finder.castView(view, 2131230843, "field '_fbLogin'");
  }

  @Override public void reset(T target) {
    target._emailText = null;
    target._passwordText = null;
    target._loginButton = null;
    target._signupLink = null;
    target._fbLogin = null;
  }
}
