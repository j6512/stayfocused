package com.j6512.stayfocused.models.data;

import com.j6512.stayfocused.models.TaskList;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskListRepository extends CrudRepository<TaskList, Integer> {
}
