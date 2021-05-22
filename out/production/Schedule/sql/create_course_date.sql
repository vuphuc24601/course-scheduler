CREATE TABLE course_date (
id INT UNSIGNED NOT NULL
AUTO_INCREMENT PRIMARY KEY,
course_id VARCHAR(60) NOT NULL,
FOREIGN KEY (course_id)
	REFERENCES course(course_id),
start_time TIME NOT NULL,
end_time TIME NOT NULL,
day INT NOT NULL,
description VARCHAR(60)
)

