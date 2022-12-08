package parser;

import com.google.gson.Gson;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Accessors(chain = true)
public class Event implements Serializable {

    @NonNull
    private String name;
    private Object target;
    private Boolean attachToShadows;
    private Boolean recurseFrames;

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
