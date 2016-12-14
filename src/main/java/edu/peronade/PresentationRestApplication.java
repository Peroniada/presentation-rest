package edu.peronade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Component;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.List;
import java.util.stream.Stream;

@SpringBootApplication
public class PresentationRestApplication {

	public static void main(String[] args) {
		SpringApplication.run(PresentationRestApplication.class, args);
	}
}


@Entity
class Task {

	@GeneratedValue(strategy = GenerationType.AUTO)
	@Id
	private Long id;

	private String taskName;

	public Task(String taskName) {
		this.taskName = taskName;
	}

	public Task() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
}

@RepositoryRestResource(collectionResourceRel = "tasks", path = "tasks")
interface TaskRepository extends PagingAndSortingRepository<Task,Long> {
	List<Task> findById(@Param("id") Long id);
}

@Component
class SampleData implements  CommandLineRunner {

	private TaskRepository taskRepository;

	@Autowired
	public SampleData(TaskRepository taskRepository) {
		this.taskRepository = taskRepository;
	}

	@Override
	public void run(String... args) throws Exception {
		Stream.of("zjesc","kodzic","spac","spac wiecej").forEach(task -> taskRepository.save(new Task(task)));
	}
}