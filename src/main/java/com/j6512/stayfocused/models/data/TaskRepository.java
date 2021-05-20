package com.j6512.stayfocused.models.data;

import com.j6512.stayfocused.models.Task;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends CrudRepository<Task, Integer> {

    Iterable<Task> getAllTasksByTaskListId(int task_list_id);
}
