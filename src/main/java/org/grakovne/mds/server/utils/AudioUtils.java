package org.grakovne.mds.server.utils;

import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.AudioHeader;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.TagException;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;

/**
 * Works with mp3 files of stories.
 */

@Component
public class AudioUtils {

    /**
     * Returns length of audio stream.
     *
     * @param audioFile mp3 file object
     * @return audio duration in seconds
     */

    public Integer getAudioFileLength(File audioFile) {
        AudioHeader header = getHeader(audioFile);
        return header.getTrackLength();
    }

    /**
     * Returns mp3 bitrate.
     *
     * @param audioFile mp3 file object
     * @return bitrate in kb/s
     */

    public Long getAudioFileBitrate(File audioFile) {
        AudioHeader header = getHeader(audioFile);
        return header.getBitRateAsNumber();
    }

    /**
     * Returns file size of audio stream.
     *
     * @param audioFile mp3 file object
     * @return size of file in bytes
     */

    public Long getAudioFileSize(File audioFile) {
        return audioFile.length();
    }

    public String getAudioFileTitle(File file) {
        return getAudioFile(file).getTag().getFirst(FieldKey.TITLE);
    }

    public String getAudioFileArtist(File file) {
        return getAudioFile(file).getTag().getFirst(FieldKey.ARTIST);
    }

    private AudioFile getAudioFile(File file) {
        try {
            return AudioFileIO.read(file);
        } catch (CannotReadException
            | IOException
            | TagException
            | ReadOnlyFileException
            | InvalidAudioFrameException e) {

            throw new IllegalArgumentException(e.getMessage());
        }
    }

    private AudioHeader getHeader(File file) {
        return getAudioFile(file).getAudioHeader();
    }
}
