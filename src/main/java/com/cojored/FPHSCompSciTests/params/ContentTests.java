package com.cojored.FPHSCompSciTests.params;

import org.json.JSONObject;

public class ContentTests {
    public boolean hasNoAndOr;

    public boolean heading;

    public boolean conditionalFormat;

    public ContentTests(JSONObject obj) {
        hasNoAndOr = (boolean) obj.get("hasNoAndOr");
        heading = (boolean) obj.get("heading");
        conditionalFormat = (boolean) obj.get("conditionalFormat");
    }
}
