SELECT c.course_id, c.title, c.credit, cd.start_time, cd.end_time, round(time_to_sec(timediff(end_time, start_time))/60) as duration, day, cd.description
FROM course c
JOIN schedule_1 cd
WHERE (c.course_id = cd.course_id);
