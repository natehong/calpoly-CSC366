-- given (check in, check out, view, bed type) list available rooms
-- all attributes with a 'REQUESTED' prefix should be replaced in the java level
SELECT room_code, ocean_view, bed_type
FROM rooms
WHERE ocean_view = REQUESTED_view AND
bed_type = REQUESTED_bed_type AND
room_code NOT IN
   (SELECT DISTINCT room AS reserved_rooms
   FROM reservations
   WHERE ocean_view = REQUESTED_view
   AND bed_type = REQUESTED_bed_type
   AND ((check_in BETWEEN REQUESTED_check_in AND REQUESTED_check_out)
   OR (check_out BETWEEN REQUESTED_check_in AND REQUESTED_check_out)))
ORDER BY room_code;

-- example query
SELECT room_code, ocean_view, bed_type
FROM rooms
WHERE ocean_view = 'true' AND
bed_type = 'double queen' AND
room_code NOT IN
   (SELECT DISTINCT room AS reserved_rooms
   FROM reservations
   WHERE ocean_view = 'true'
   AND bed_type = 'double queen'
   AND ((check_in BETWEEN '2018-01-03' AND '2018-01-05')
   OR (check_out BETWEEN '2018-01-03' AND '2018-01-05')))
ORDER BY room_code;