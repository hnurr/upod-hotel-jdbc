package service;

public class ModelServices {

    private int serviceId;
    private String serviceName;
    private double serviceCost;

    public ModelServices(int serviceId, String serviceName, double serviceCost) {
        this.serviceId = serviceId;
        this.serviceName = serviceName;
        this.serviceCost = serviceCost;

    }

    public ModelServices( String serviceName, double serviceCost) {

        this.serviceName = serviceName;
        this.serviceCost = serviceCost;

    }


    public int getServiceId() {
        return serviceId;
    }

    public void setServiceId(int serviceId) {
        this.serviceId = serviceId;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public double getServiceCost() {
        return serviceCost;
    }

    public void setServiceCost(int serviceCost) {
        this.serviceCost = serviceCost;
    }

}
