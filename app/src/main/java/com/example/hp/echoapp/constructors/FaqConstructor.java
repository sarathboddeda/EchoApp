package com.example.hp.echoapp.constructors;

/**
 * Created by HP on 2/3/2018.
 */

public class FaqConstructor {
    String faqquestion,faqanswer,faqnub;

    public FaqConstructor(String faqnub,String faqquestion, String faqanswer) {
        this.faqquestion = faqquestion;
        this.faqanswer = faqanswer;
        this.faqnub = faqnub;
    }

    public String getFaqquestion() {
        return faqquestion;
    }

    public void setFaqquestion(String faqquestion) {
        this.faqquestion = faqquestion;
    }

    public String getFaqanswer() {
        return faqanswer;
    }

    public void setFaqanswer(String faqanswer) {
        this.faqanswer = faqanswer;
    }

    public String getFaqnub() {
        return faqnub;
    }

    public void setFaqnub(String faqnub) {
        this.faqnub = faqnub;
    }
}
