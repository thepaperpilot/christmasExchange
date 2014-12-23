import org.json.simple.JSONObject;

import javax.swing.*;

interface Group {

public String getName();

public void setName(String name);

public Box toCards();

public JSONObject toJSON();

public String toCSV();

public void randomize();

Person find(String name);
}
