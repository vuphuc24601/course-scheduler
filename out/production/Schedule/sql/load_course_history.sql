SELECT DISTINCT(c.course_id), c.title, c.credit, c.description, c.color
FROM course c
JOIN schedule_1 cd
WHERE (c.course_id = cd.course_id);