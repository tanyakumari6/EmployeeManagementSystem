package net.javaguides.springboot.model;



import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "employees", uniqueConstraints = @UniqueConstraint(columnNames = "email"))
public class Employee {
	
	@Id
	@GeneratedValue(strategy =  GenerationType.IDENTITY)
	private long id;
	
	@Column(name = "first_name")
	private String firstName;
	
	@Column(name = "last_name")
	private String lastName;
	
	@Column(name = "email")
	private String email;
	
	@Column(name = "dept_name")
	private String deptName;
	
	@Column(name = "password")
	private String password;

	@ManyToMany(fetch=FetchType.EAGER, cascade=CascadeType.ALL)
	@JoinTable(
			name="employee_roles",
			joinColumns=@JoinColumn(
					name="employee_id", referencedColumnName="id"),
			inverseJoinColumns=@JoinColumn(name="role_id", referencedColumnName="id"))
	private Collection<Role> roles;
	public Collection<Role> getRoles() {
		return roles;
	}
	public void setRoles(Collection<Role> roles) {
		this.roles = roles;
	}

	public Employee(String firstName, String lastName, String email, String deptName, String password,
					Collection<Role> roles) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.deptName = deptName;
		this.password = password;
		this.roles = roles;
	}
	
	public Employee(String firstName2, String lastName2, String email2, String password2, Collection<Role> roles) {
		// TODO Auto-generated constructor stub
	}
	public Employee() {
		// TODO Auto-generated constructor stub
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
}