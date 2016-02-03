package com.nullnothing.relationshipstats.graphing;

import android.graphics.Color;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class XMLColourPullParserHandler {

    private List<Integer> colours;

    public XMLColourPullParserHandler() {
        colours = new ArrayList<>();
    }

    public List<Integer> parse(InputStream is) {
        boolean colourTag = false;
        XmlPullParserFactory factory = null;
        XmlPullParser parser = null;

        try {
            factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            parser = factory.newPullParser();

            parser.setInput(is, null);

            int eventType = parser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {

                String tag = parser.getName();
                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        if(tag.equals("color")) colourTag = true;
                        break;
                    case XmlPullParser.TEXT:
                        if(colourTag) {
                            colours.add(Color.parseColor(parser.getText()));
                            colourTag = false;
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        break;
                    default:
                        break;
                }
                eventType = parser.next();
            }

        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return colours;
    }
}
