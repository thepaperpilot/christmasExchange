import org.json.simple.JSONObject;

import javax.swing.*;

public interface Group {

public String getName();

public void setName(String name);

public JPanel toCards();

public JSONObject toJSON();

public String toCSV();

public void clear();

public void randomize();
}
