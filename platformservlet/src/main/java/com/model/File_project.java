package com.model;



import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter 
@Setter
@Entity
@Table(name = "file_project")
public class File_project {
	@Id
	private int id;
	private String url;
	private int prj_id;
    private int ispassed;
    private Integer step_id;
}
