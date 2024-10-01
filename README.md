# Homework Assignment

A Java web application that displays the current time based on a user-specified or stored timezone.

## Features

- **Time Display**: Shows the current time in UTC by default or based on a specified timezone.
- **Timezone Parameter**: Users can provide a `timezone` query parameter (e.g., `/time?timezone=America/New_York`) to view the current time in that zone.
- **Cookie Support**: Valid timezones are saved in a `lastTimezone` cookie for future visits.
- **Timezone Validation**: Invalid timezones return a "400 Invalid timezone" error.
- **Time Format**: Displays time in `yyyy-MM-dd HH:mm:ss z` format.