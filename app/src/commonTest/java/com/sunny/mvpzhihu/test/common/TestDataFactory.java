package com.sunny.mvpzhihu.test.common;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.sunny.mvpzhihu.data.model.bean.Subject;
import com.sunny.mvpzhihu.data.model.entity.InTheatersEntity;
import com.sunny.mvpzhihu.data.model.pojo.Image;
import com.sunny.mvpzhihu.data.model.pojo.Person;
import com.sunny.mvpzhihu.data.model.pojo.Rating;

/**
 * Factory class that makes instances of data models with random field values.
 * The aim of this class is to help setting up test fixtures.
 */
public class TestDataFactory {

    public static String randomUuid() {
        return UUID.randomUUID().toString();
    }

    public static InTheatersEntity makeInTheatersEntity(int count) {
        return InTheatersEntity.create(count, 0, 32,  "正在上映的电影-北京", makeListSubject(count));
    }

    public static List<Subject> makeListSubject(int number) {
        List<Subject> subjects = new ArrayList<>();
        for (int i = 0; i < number; i++) {
            subjects.add(makeSubject(randomUuid()));
        }
        return subjects;
    }

    public static Subject makeSubject(String uniqueSuffix) {
        return Subject.builder()
                .id(uniqueSuffix)
                .rating(makeRating())
                .genres(makeGenres())
                .title("大话西游3")
                .casts(makeListPerson(3))
                .collect_count(17903)
                .original_title("大话西游3")
                .subtype("movie")
                .directors(makeListPerson(1))
                .year("2016")
                .images(makeImage())
                .alt("https://movie.douban.com/subject/26284595/")
                .build();
    }

    public static Rating makeRating() {
        return Rating.create(10, 4.1, "20", 0);
    }

    public static List<String> makeGenres() {
        List<String> genres = new ArrayList<>();
        genres.add("喜剧");
        genres.add("爱情");
        genres.add("奇幻");
        return genres;
    }

    public static Image makeImage() {
        return Image.create("https://img3.doubanio.com/img/celebrity/small/14025.jpg",
                "https://img3.doubanio.com/img/celebrity/large/14025.jpg",
                "https://img3.doubanio.com/img/celebrity/medium/14025.jpg");
    }

    public static Person makePerson() {
        return Person.create("https://movie.douban.com/celebrity/1275667/",
                makeImage(), "韩庚", "1275667");
    }

    public static List<Person> makeListPerson(int number) {
        List<Person> persons = new ArrayList<>();
        for (int i = 0; i < number; i++) {
            persons.add(makePerson());
        }
        return persons;
    }

}
