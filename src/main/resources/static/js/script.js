document.addEventListener('DOMContentLoaded', function() {
    const searchBtn = document.getElementById('search-btn');
    const cityInput = document.getElementById('city-input');
    const weatherInfo = document.getElementById('weather-info');
    const errorMessage = document.getElementById('error-message');

    // Event listener for search button
    searchBtn.addEventListener('click', fetchWeatherData);

    // Event listener for Enter key in input field
    cityInput.addEventListener('keypress', function(event) {
        if (event.key === 'Enter') {
            fetchWeatherData();
        }
    });

    // Function to fetch weather data from our backend API
    function fetchWeatherData() {
        const cityName = cityInput.value.trim();
        
        if (cityName === '') {
            showError('Please enter a city name');
            return;
        }

        // Show loading state
        weatherInfo.innerHTML = '<p class="loading">Loading weather data...</p>';
        weatherInfo.classList.add('active');
        errorMessage.style.display = 'none';

        // Fetch weather data from our API
        fetch(`/weather/city/${cityName}`)
            .then(response => {
                if (!response.ok) {
                    throw new Error('City not found or network error');
                }
                return response.json();
            })
            .then(data => {
                displayWeatherData(data);
            })
            .catch(error => {
                showError(error.message);
                weatherInfo.classList.remove('active');
            });
    }

    // Function to display weather data
    function displayWeatherData(data) {
        // Get weather icon based on description
        const iconUrl = getWeatherIcon(data.description.toLowerCase());
        
        // Format date
        const dateTime = new Date(data.dateTime.replace(' ', 'T'));
        const formattedDate = dateTime.toLocaleDateString('en-US', {
            weekday: 'long',
            year: 'numeric',
            month: 'long',
            day: 'numeric'
        });

        // Build HTML for weather information
        const html = `
            <h2 class="city-name">${data.cityName}, ${data.countryCode}</h2>
            <p class="date">${formattedDate}</p>
            <img src="${iconUrl}" alt="${data.description}" class="weather-icon">
            <p class="temperature">${Math.round(data.temp)}°C</p>
            <p class="description">${data.description}</p>
            <div class="details">
                <div class="col">
                    <p class="value">${data.rh}%</p>
                    <p class="label">Humidity</p>
                </div>
                <div class="col">
                    <p class="value">${data.countryCode}</p>
                    <p class="label">Country</p>
                </div>
            </div>
        `;

        // Update DOM
        weatherInfo.innerHTML = html;
        weatherInfo.classList.add('active');
    }

    // Function to show error message
    function showError(message) {
        errorMessage.textContent = message;
        errorMessage.style.display = 'block';
    }

    // Function to get appropriate weather icon based on description
    function getWeatherIcon(description) {
        const icons = {
            'clear': 'https://cdn-icons-png.flaticon.com/512/6974/6974833.png',
            'sunny': 'https://cdn-icons-png.flaticon.com/512/6974/6974833.png',
            'clouds': 'https://cdn-icons-png.flaticon.com/512/6974/6974833.png',
            'partly cloudy': 'https://cdn-icons-png.flaticon.com/512/1146/1146869.png',
            'scattered clouds': 'https://cdn-icons-png.flaticon.com/512/1146/1146869.png',
            'broken clouds': 'https://cdn-icons-png.flaticon.com/512/1146/1146869.png',
            'cloudy': 'https://cdn-icons-png.flaticon.com/512/1163/1163661.png',
            'overcast': 'https://cdn-icons-png.flaticon.com/512/1163/1163661.png',
            'mist': 'https://cdn-icons-png.flaticon.com/512/4005/4005901.png',
            'fog': 'https://cdn-icons-png.flaticon.com/512/4005/4005901.png',
            'rain': 'https://cdn-icons-png.flaticon.com/512/3351/3351979.png',
            'light rain': 'https://cdn-icons-png.flaticon.com/512/3351/3351979.png',
            'moderate rain': 'https://cdn-icons-png.flaticon.com/512/3351/3351979.png',
            'heavy rain': 'https://cdn-icons-png.flaticon.com/512/3351/3351979.png',
            'showers': 'https://cdn-icons-png.flaticon.com/512/3351/3351979.png',
            'thunderstorm': 'https://cdn-icons-png.flaticon.com/512/1959/1959348.png',
            'snow': 'https://cdn-icons-png.flaticon.com/512/642/642102.png',
            'light snow': 'https://cdn-icons-png.flaticon.com/512/642/642102.png',
            'heavy snow': 'https://cdn-icons-png.flaticon.com/512/642/642102.png'
        };

        // Find matching icon or use default
        for (const key in icons) {
            if (description.includes(key)) {
                return icons[key];
            }
        }
        
        // Default icon if no match found
        return 'https://cdn-icons-png.flaticon.com/512/1779/1779940.png';
    }
});
