package io.jenkins.plugins.coverapi.render;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("render")
public class LineRateRender extends Render {
    @XStreamAlias("rate")
    private double lineRate;
    @XStreamAlias("sum")
    private int sum;
    @XStreamAlias("part")
    private int part;

    public LineRateRender() {
    }

    public LineRateRender(double lineRate, int sum, int part) {
        this.lineRate = lineRate;
        this.sum = sum;
        this.part = part;
    }

    public double getLineRate() {
        return lineRate;
    }

    public void setLineRate(double lineRate) {
        this.lineRate = lineRate;
    }

    public int getSum() {
        return sum;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }

    public int getPart() {
        return part;
    }

    public void setPart(int part) {
        this.part = part;
    }
}
