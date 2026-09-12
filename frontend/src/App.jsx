import { useState } from 'react'
import './App.css'
import Itinerary from './components/Itinerary'
import TripSummary from './components/TripSummary'
import AIPlan from './components/AIPlan'

function App() {
  const [destination, setDestination] = useState('')
  const [days, setDays] = useState(3)
  const [budget, setBudget] = useState(1000)
  const [interests, setInterests] = useState([])

  const [travelPlan, setTravelPlan] = useState(null)
  const [loading, setLoading] = useState(false)
  const [error, setError] = useState('')

  const availableInterests = [
    'History',
    'Art',
    'Food',
    'Nature',
    'Shopping',
    'Nightlife',
    'Architecture'
  ]

  function toggleInterest(interest) {
    if (interests.includes(interest)) {
      setInterests(interests.filter(item => item !== interest))
    } else {
      setInterests([...interests, interest])
    }
  }

  async function generateTrip() {
    if (!destination.trim()) {
      setError('Please enter a destination.')
      return
    }

    if (days < 1) {
      setError('Trip duration must be at least 1 day.')
      return
    }

    if (budget <= 0) {
      setError('Budget must be greater than 0.')
      return
    }

    setLoading(true)
    setError('')
    setTravelPlan(null)

    try {
      const response = await fetch(
        '/api/travel-plans',
        {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json'
          },
          body: JSON.stringify({
            destination,
            days,
            budget,
            interests: interests.map(
              interest => interest.toLowerCase()
            )
          })
        }
      )

      if (!response.ok) {
        let message = 'Failed to generate travel plan.'

        try {
          const errorData = await response.json()

          if (errorData.error) {
            message = errorData.error
          }
        } catch {
          // Backend did not return JSON.
        }

        throw new Error(message)
      }

      const data = await response.json()
      setTravelPlan(data)

    } catch (error) {
      if (error instanceof TypeError) {
        setError(
          'Could not connect to TourVista. Make sure the backend is running.'
        )
      } else {
        setError(error.message)
      }
    } finally {
      setLoading(false)
    }
  }

  function startNewTrip() {
    setTravelPlan(null)
    setError('')
    setDestination('')
    setDays(3)
    setBudget(1000)
    setInterests([])

    window.scrollTo({
      top: 0,
      behavior: 'smooth'
    })
  }

  return (
    <div className="app">
      <header className="navbar">
        <div className="logo">TourVista</div>

        <nav>
          <a href="#planner">Plan a trip</a>
          <a href="#about">About</a>
        </nav>
      </header>

      <main id="planner">
        <section className="hero">
          <p className="eyebrow">YOUR NEXT JOURNEY</p>

          <h1>
            Plan a trip
            <br />
            worth remembering.
          </h1>

          <p className="hero-text">
            Tell us where you're going, what you enjoy,
            and we'll build the itinerary around you.
          </p>
        </section>

        <section className="planner-card">
          <div className="form-group destination-group">
            <label>Destination</label>

            <input
              type="text"
              placeholder="Where are you going?"
              value={destination}
              onChange={event => setDestination(event.target.value)}
            />
          </div>

          <div className="form-row">
            <div className="form-group">
              <label>Days</label>

              <input
                type="number"
                min="1"
                value={days}
                onChange={event =>
                  setDays(Number(event.target.value))
                }
              />
            </div>

            <div className="form-group">
              <label>Budget</label>

              <input
                type="number"
                min="1"
                value={budget}
                onChange={event =>
                  setBudget(Number(event.target.value))
                }
              />
            </div>
          </div>

          <div className="form-group">
            <label>What are you interested in?</label>

            <div className="interest-list">
              {availableInterests.map(interest => (
                <button
                  key={interest}
                  type="button"
                  className={
                    interests.includes(interest)
                      ? 'interest selected'
                      : 'interest'
                  }
                  onClick={() => toggleInterest(interest)}
                >
                  {interest}
                </button>
              ))}
            </div>
          </div>

          {error && (
            <p className="error">
              {error}
            </p>
          )}

          {loading && (
            <div className="loading-message">
              <span className="loading-line"></span>
              <p>Building your itinerary...</p>
            </div>
          )}

          <button
            className="generate-button"
            type="button"
            onClick={generateTrip}
            disabled={loading}
          >
            {loading
              ? 'Planning your trip...'
              : 'Generate my trip'}
          </button>
        </section>

        {travelPlan && (
          <section className="results">
            <TripSummary
              destination={destination}
              days={days}
              budget={budget}
              interests={interests}
            />

            <Itinerary
              days={travelPlan.itinerary.days}
            />

            <AIPlan
              plan={travelPlan.aiPlan}
            />

            <button
              className="new-trip-button"
              type="button"
              onClick={startNewTrip}
            >
              Plan another trip
            </button>
          </section>
        )}
        
      </main>
      <section id="about" className="about-section">
        <div className="about-inner">
          <div className="section-heading">
            <p className="eyebrow">ABOUT TOURVISTA</p>

            <h2>Travel planning, built around you.</h2>
          </div>

          <p className="about-text">
            TourVista combines places, weather, recommendations,
            itinerary optimization, and AI-generated guidance to
            create a travel plan based on your destination,
            interests, trip length, and budget.
          </p>
        </div>
      </section>

    </div>
  )
}

export default App