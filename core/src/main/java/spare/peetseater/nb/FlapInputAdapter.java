package spare.peetseater.nb;

import com.badlogic.gdx.InputAdapter;

import java.util.LinkedList;
import java.util.List;

public class FlapInputAdapter extends InputAdapter {

    private final float timeBetweenClicksSeconds;
    private float timer;
    private boolean isOnCooldown;
    private List<FlapInputSubscriber> subscribers;

    public FlapInputAdapter(float timeBetweenClicksSeconds) {
        this.timeBetweenClicksSeconds = timeBetweenClicksSeconds;
        this.isOnCooldown = false;
        this.timer = 0;
        this.subscribers = new LinkedList<>();
    }

    public void addSubscriber(FlapInputSubscriber subscriber) {
        subscribers.add(subscriber);
    }

    public void update(float delta) {
        this.timer = Math.max(0, this.timer - delta);
        if (this.timer == 0) {
            this.isOnCooldown = false;
        }
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (isOnCooldown) {
            return super.touchDown(screenX, screenY, pointer, button);
        }
        this.isOnCooldown = true;
        this.timer = timeBetweenClicksSeconds;
        for (FlapInputSubscriber subscriber : subscribers) {
            subscriber.onFlapInput();
        }
        return true;
    }

}
