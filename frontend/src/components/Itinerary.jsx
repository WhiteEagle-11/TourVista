function Itinerary({ days }) {
    return (
        <section className="itinerary">
            <div className="section-heading">
                <p className="eyebrow">YOUR ROUTE</p>
                <h2>Your itinerary</h2>
            </div>

            <div className="days">
                {days.map((day) => (
                    <article className="day-card" key={day.day}>
                        <div className="day-number">
                            DAY {day.day}
                        </div>

                        <div className="day-content">
                            {day.recommendations.length === 0 ? (
                                <p className="empty-day">
                                    No attractions scheduled.
                                </p>
                            ) : (
                                day.recommendations.map((recommendation) => (
                                    <div
                                        className="place"
                                        key={recommendation.place.placeId}
                                    >
                                        <div className="place-info">
                                            <h3>{recommendation.place.name}</h3>

                                            <p className="place-address">
                                                {recommendation.place.address}
                                            </p>

                                            <p className="place-reason">
                                                {recommendation.reason}
                                            </p>
                                        </div>

                                        <div className="place-score">
                                            <span>Match</span>
                                            <strong>
                                                {recommendation.score.toFixed(0)}
                                            </strong>
                                        </div>
                                    </div>
                                ))
                            )}
                        </div>
                    </article>
                ))}
            </div>
        </section>
    )
}

export default Itinerary