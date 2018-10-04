// Generated code from Butter Knife. Do not modify!
package grupo9.usjt.usjt.com.activities;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.Injector;

public class MapActivity$$ViewInjector<T extends grupo9.usjt.usjt.com.activities.MapActivity> implements Injector<T> {
  @Override public void inject(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131230921, "field 'textSearch'");
    target.textSearch = finder.castView(view, 2131230921, "field 'textSearch'");
  }

  @Override public void reset(T target) {
    target.textSearch = null;
  }
}
