package model.entity;

public class TourGuideModel {
    private final int guideID;
    private final String guideName;
    private final int age;
    private final int experience;
    private final float salary;

    public TourGuideModel(int guideID, String guideName, int age, int experience, float salary) {
        this.guideID = guideID;
        this.guideName = guideName;
        this.age = age;
        this.experience = experience;
        this.salary = salary;
    }

    public int getGuideID() {return guideID; }

    public String getGuide_name() {return guideName; }

    public int getAge() {return age; }

    public int getExperience() {return experience; }
    public float getSalary() {return salary; }
}
