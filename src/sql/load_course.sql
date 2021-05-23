SELECT c.course_id, c.title, c.credit, cd.day, cd.description, cd.start_time, cd.end_time, timediff(cd.end_time, cd.start_time) AS duration
FROM course_date cd
JOIN course c ON c.course_id = cd.course_id 