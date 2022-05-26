package com.example.androidproject.Model.Database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Dao
public interface DayStatusDao {
    @Insert
    void insert(DayStatus dayStatus);

    @Delete
    void delete(DayStatus dayStatus);

    @Query("Delete FROM DayStatus WHERE date = :date")
    void deleteByDate(String date);

    @Query("Select * from DayStatus")
    List<DayStatus> getAll();

    @Query("Select * from DayStatus WHERE date LIKE :date || '%'")
    List<DayStatus> getAllBySelectedDate(String date);

    @Query("Select * from DayStatus WHERE date = :date")
    DayStatus getOneByDate(String date);

    @Query("SELECT COUNT(*) FROM DAYSTATUS WHERE rainy = 1 AND date LIKE :date || '%'")
    int getRainyCount(String date);

    @Query("SELECT COUNT(*) FROM DAYSTATUS WHERE sunny = 1 AND date LIKE :date || '%'")
    int getSunnyCount(String date);

    @Query("SELECT COUNT(*) FROM DAYSTATUS WHERE snowy = 1 AND date LIKE :date || '%'")
    int getSnowyCount(String date);

    @Query("SELECT COUNT(*) FROM DAYSTATUS WHERE windy = 1 AND date LIKE :date || '%'")
    int getWindyCount(String date);

    @Query("SELECT COUNT(*) FROM DAYSTATUS WHERE cloudy = 1 AND date LIKE :date || '%'")
    int getCloudyCount(String date);

    @Query("SELECT COUNT(*) FROM DAYSTATUS WHERE family = 1 AND date LIKE :date || '%'")
    int getFamilyCount(String date);

    @Query("SELECT COUNT(*) FROM DAYSTATUS WHERE friends = 1 AND date LIKE :date || '%'")
    int getFriendsCount(String date);

    @Query("SELECT COUNT(*) FROM DAYSTATUS WHERE GFBF = 1 AND date LIKE :date || '%'")
    int getGFBFCount(String date);

    @Query("SELECT COUNT(*) FROM DAYSTATUS WHERE acquaintance = 1 AND date LIKE :date || '%'")
    int getAcquaintanceCount(String date);

    @Query("SELECT COUNT(*) FROM DAYSTATUS WHERE none = 1 AND date LIKE :date || '%'")
    int getNoneCount(String date);

    @Query("SELECT COUNT(*) FROM DayStatus WHERE date LIKE :date || '%'")
    int countByDate(String date);

    @Query("SELECT COUNT(*) FROM DayStatus WHERE emotion_type = :emotion_type AND date LIKE :date || '%'")
    int countEmotionTypeByDate(String emotion_type, String date);

    @Query("UPDATE DayStatus SET emotion_type = :emotion_type, note = :note, photo_URL = :photo_URL" +
            ", sunny = :sunny, rainy = :rainy, cloudy = :cloudy, snowy = :snowy, windy = :windy" +
            ", friends = :friends, family = :family, acquaintance = :acquaintance, GFBF = :GFBF, none = :none WHERE date = :date")
    int update(String emotion_type, String note, String photo_URL, boolean sunny
                            , boolean rainy, boolean cloudy, boolean snowy, boolean windy
        , boolean friends, boolean family, boolean acquaintance, boolean GFBF, boolean none, String date);
}
