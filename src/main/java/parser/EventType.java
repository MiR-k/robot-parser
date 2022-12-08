package parser;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
public enum EventType {

    change(null, true, true),
    click(null, true, true),
    load("window", null, null),
    mousemove(null, null, true);

    private Event event;
    @Setter
    private Map ivent;

    EventType(String target, Boolean attachToShadows, Boolean recurseFrames) {
        this.event = new Event(this.name(), target, attachToShadows, recurseFrames);
    }

    @Override
    public String toString() {
        return this.event.toString();
    }
}
