function TripSummary({ destination, days, budget, interests }) {
    return (
        <section className="trip-summary">
            <div className="summary-main">
                <p className="eyebrow">YOUR TRIP</p>

                <h2>{destination}</h2>

                <p className="summary-meta">
                    {days} {days === 1 ? 'day' : 'days'} · Budget {budget}
                </p>

                {interests.length > 0 && (
                    <div className="summary-interests">
                        {interests.map((interest) => (
                            <span key={interest}>{interest}</span>
                        ))}
                    </div>
                )}
            </div>
        </section>
    )
}

export default TripSummary