// Generated code from Butter Knife. Do not modify!
package grupo9.usjt.usjt.com.activities;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.Injector;

public class SignUpActivity$$ViewInjector<T extends grupo9.usjt.usjt.com.activities.SignUpActivity> implements Injector<T> {
  @Override public void inject(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131230830, "field '_nameText'");
    target._nameText = finder.castView(view, 2131230830, "field '_nameText'");
    view = finder.findRequiredView(source, 2131230829, "field '_emailText'");
    target._emailText = finder.castView(view, 2131230829, "field '_emailText'");
    view = finder.findRequiredView(source, 2131230831, "field '_passwordText'");
    target._passwordText = finder.castView(view, 2131230831, "field '_passwordText'");
    view = finder.findRequiredView(source, 2131230764, "field '_signupButton'");
    target._signupButton = finder.castView(view, 2131230764, "field '_signupButton'");
    view = finder.findRequiredView(source, 2131230840, "field '_loginLink'");
    target._loginLink = finder.castView(view, 2131230840, "field '_loginLink'");
  }

  @Override public void reset(T target) {
    target._nameText = null;
    target._emailText = null;
    target._passwordText = null;
    target._signupButton = null;
    target._loginLink = null;
  }
}
