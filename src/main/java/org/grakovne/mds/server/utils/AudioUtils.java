package org.grakovne.mds.server.utils;

import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.AudioHeader;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.TagException;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;

@Component
public class AudioUtils {

    public Integer getAudioFileLength(File audioFile) {
        AudioHeader header = getHeader(audioFile);
        return header.getTrackLength();
    }

    public Long getAudioFileBitrate(File audioFile) {
        AudioHeader header = getHeader(audioFile);
        return header.getBitRateAsNumber();
    }

    public Long getAudioFileSize(File audioFile) {
        return audioFile.length();
    }

    private AudioHeader getHeader(File file) {
        AudioFile audioFile = null;
        try {
            audioFile = AudioFileIO.read(file);
        } catch (CannotReadException | IOException | TagException | InvalidAudioFrameException | ReadOnlyFileException e) {
            throw new IllegalArgumentException("Incorrect audio File");
        }

        return audioFile.getAudioHeader();
    }
}
