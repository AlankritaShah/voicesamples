package alankrita.in.voicesamples;

public class User_Info {
    String user_id;
    String user_name;
    String user_age;
    String user_sex;
    String user_language;
    String user_phone_brand;
    String user_phone_model;
    String user_phone_version;
    int user_phone_api;
    String time;
    String date;


    public User_Info(String user_id,
                     String user_name,
                     String user_age,
                     String user_sex,
                     String user_language,
                     String user_phone_brand,
                     String user_phone_model,
                     String user_phone_version,
                     int user_phone_api,
                     String date,
                     String time) {
        this.user_id = user_id;
        this.user_name = user_name;
        this.user_age = user_age;
        this.user_sex = user_sex;
        this.user_language = user_language;
        this.user_phone_brand = user_phone_brand;
        this.user_phone_model = user_phone_model;
        this.user_phone_version = user_phone_version;
        this.user_phone_api = user_phone_api;
        this.time=time;
        this.date=date;


    }

    public String getUser_id() {
        return user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public String getUser_age() {
        return user_age;
    }

    public String getUser_sex() {
        return user_sex;
    }

    public String getUser_language() {
        return user_language;
    }

    public String getUser_phone_brand() {
        return user_phone_brand;
    }

    public void setUser_phone_brand(String user_phone_brand) {
        this.user_phone_brand = user_phone_brand;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUser_phone_model() {
        return user_phone_model;
    }

    public void setUser_phone_model(String user_phone_model) {
        this.user_phone_model = user_phone_model;
    }

    public String getUser_phone_version() {
        return user_phone_version;
    }

    public void setUser_phone_version(String user_phone_version) {
        this.user_phone_version = user_phone_version;
    }

    public int getUser_phone_api() {
        return user_phone_api;
    }

    public void setUser_phone_api(int user_phone_api) {
        this.user_phone_api = user_phone_api;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public void setUser_age(String user_age) {
        this.user_age = user_age;
    }

    public void setUser_sex(String user_sex) {
        this.user_sex = user_sex;
    }

    public void setUser_language(String user_language) {
        this.user_language = user_language;
    }

}