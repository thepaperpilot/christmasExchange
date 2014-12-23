import org.json.simple.JSONObject;

import javax.swing.*;
import java.util.ArrayList;

interface Group {

public String getName();

public void setName(String name);

public ArrayList<Person> getPeople();

public Box toCards();

public JSONObject toJSON();

public String toCSV();

public void randomize();

Person find(String name);
}
