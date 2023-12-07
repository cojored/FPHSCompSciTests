package com.cojored.FPHSCompSciTests.params;

import org.json.JSONObject;

public class ContentTests {
    public final boolean hasNoAndOr;

    public final boolean heading;

    public final boolean conditionalFormat;

    public ContentTests(JSONObject obj) {
        hasNoAndOr = (boolean) obj.get("hasNoAndOr");
        heading = (boolean) obj.get("heading");
        conditionalFormat = (boolean) obj.get("conditionalFormat");
    }
}
