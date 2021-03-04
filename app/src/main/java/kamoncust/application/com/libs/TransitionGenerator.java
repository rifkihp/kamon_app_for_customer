package kamoncust.application.com.libs;



        import android.graphics.RectF;

public interface TransitionGenerator {

    Transition generateNextTransition(RectF drawableBounds, RectF viewport);

}