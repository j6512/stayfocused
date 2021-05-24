package com.j6512.stayfocused.models.data;

import com.j6512.stayfocused.models.Notes;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotesRepository extends CrudRepository<Notes, Integer> {
}
