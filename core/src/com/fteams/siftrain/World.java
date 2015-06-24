package com.fteams.siftrain;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.utils.Array;
import com.fteams.siftrain.assets.Assets;
import com.fteams.siftrain.controller.SongLoader;
import com.fteams.siftrain.entities.SimpleNotesInfo;
import com.fteams.siftrain.objects.AccuracyMarker;
import com.fteams.siftrain.objects.CircleMark;
import com.fteams.siftrain.objects.ScoreDiffMarker;
import com.fteams.siftrain.objects.TapZone;

import java.util.ArrayList;
import java.util.List;

public class World {

    int width;
    int height;
    public int score;
    public int cScore;
    public int bScore;
    public int aScore;
    public int sScore;
    /**
     * The 9 zones the user can tap
     */
    Array<TapZone> zones = new Array<>();
    /**
     * The notes which spawn from the center
     */
    Array<CircleMark> marks = new Array<>();

    public int combo;
    public CircleMark.Accuracy accuracy;
    public boolean started;
    public int offsetX;
    public int offsetY;

    private Array<AccuracyMarker> accuracyMarkers;
    private Array<ScoreDiffMarker> scoreMarkers;

    public boolean paused;

    // Getters -----------
    public Array<TapZone> getZones() {
        return zones;
    }

    // --------------------

    public World() {
        createWorld();
    }

    private void createWorld() {
        Assets.music = SongLoader.loadSongFile(Assets.selectedSong.getResourceName());
        float x = 0f;
        float y = 0f;
        score = 0;
        Double noteSpeed = Assets.selectedSong.song_info[0].notes_speed;
        cScore = Assets.selectedSong.rank_info[0].rank_max;
        bScore = Assets.selectedSong.rank_info[1].rank_max;
        aScore = Assets.selectedSong.rank_info[2].rank_max;
        sScore = Assets.selectedSong.rank_info[3].rank_max;
        for (SimpleNotesInfo notesInfo : Assets.selectedSong.song_info[0].notes) {
            CircleMark mark = new CircleMark(x, y, notesInfo, noteSpeed);
            marks.add(mark);
        }
        marks.sort();

        float step = (float) (Math.PI / 8);
        float distance = 600f / 2 - 400f * 0.1275f;

        for (int i = 0; i < 9; i++) {
            float angle = step * i;
            x = (float) (distance * Math.cos(angle));
            y = -(float) (distance * Math.sin(angle));
            TapZone zone = new TapZone(x, y, i + 1);
            zones.add(zone);
        }
        accuracy = CircleMark.Accuracy.NONE;
        this.accuracyMarkers = new Array<>();
        this.scoreMarkers = new Array<>();
        paused = false;
    }

    public void setSize(int width, int height, int offsetX, int offsetY) {
        this.width = width;
        this.height = height;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
    }

    public Array<CircleMark> getMarks() {
        return marks;
    }

    public Array<AccuracyMarker> getAccuracyMarkers() {
        return accuracyMarkers;
    }

    public Array<ScoreDiffMarker> getScoreMarkers() {
        return scoreMarkers;
    }
}
