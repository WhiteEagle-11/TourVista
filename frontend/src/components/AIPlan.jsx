function renderInlineText(text) {
    const parts = text.split(/(\*\*[^*]+\*\*)/g)

    return parts.map((part, index) => {
        if (part.startsWith('**') && part.endsWith('**')) {
            return (
                <strong key={index}>
                    {part.slice(2, -2)}
                </strong>
            )
        }

        return part
    })
}

function cleanLine(line) {
    return line
        .replace(/^["']+|["']+$/g, '')
        .replace(/^\*+|\*+$/g, '')
        .trim()
}

function AIPlan({ plan }) {
    const lines = plan.split('\n')

    return (
        <section className="ai-plan">
            <div className="section-heading">
                <p className="eyebrow">TRAVEL NOTES</p>
                <h2>Your plan</h2>
            </div>

            <div className="ai-plan-content">
                {lines.map((line, index) => {
                    const trimmedLine = line.trim()

                    if (!trimmedLine) {
                        return (
                            <div
                                className="plan-space"
                                key={index}
                            />
                        )
                    }

                    const isBullet =
                        trimmedLine.startsWith('- ') ||
                        trimmedLine.startsWith('* ') ||
                        trimmedLine.startsWith('• ') ||
                        trimmedLine.startsWith('✅ ')

                    const content = isBullet
                        ? trimmedLine.replace(
                            /^(- |\* |• |✅ )/,
                            ''
                        )
                        : trimmedLine

                    const cleanedContent = cleanLine(content)

                    const isHeading =
                        trimmedLine.startsWith('###') ||
                        trimmedLine.startsWith('##') ||
                        /^Day \d+/i.test(cleanedContent)

                    if (isHeading) {
                        return (
                            <h3 key={index}>
                                {renderInlineText(
                                    cleanedContent.replace(/^#+\s*/, '')
                                )}
                            </h3>
                        )
                    }

                    if (isBullet) {
                        return (
                            <p
                                className="plan-bullet"
                                key={index}
                            >
                                {renderInlineText(cleanedContent)}
                            </p>
                        )
                    }

                    return (
                        <p key={index}>
                            {renderInlineText(cleanedContent)}
                        </p>
                    )
                })}
            </div>
        </section>
    )
}

export default AIPlan