/*Selects a game within an area, returns the sport, creator name, and name of location*/

Select g.sport, u.first_name, u.last_name, l.name
from games g, locations l, users u 
where g.location = l.id and g.creator = u.id
	and l.lat > 42.7 and l.lat < 43 and l.longi > -74 and l.longi < -73;