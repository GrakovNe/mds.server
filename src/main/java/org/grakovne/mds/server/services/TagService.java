package org.grakovne.mds.server.services;

import org.grakovne.mds.server.entity.Tag;
import org.grakovne.mds.server.repositories.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class TagService {

    @Autowired
    private TagRepository tagRepository;

    public Set<Tag> persistTagList(Set<Tag> tags) {

        if (null == tags){
            return null;
        }

        Set<Tag> persistTags = new HashSet<>();

        for (Tag tag : tags) {
            Tag savedTag = tagRepository.findAllByValue(tag.getValue());

            if (null == savedTag) {
                savedTag = tagRepository.save(tag);
            }

            persistTags.add(savedTag);
        }

        return persistTags;
    }
}
