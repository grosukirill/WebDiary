import NextHead from 'next/head'

interface HeadLayoutProps {
    title: string
    content?: any
    description?: string
}

const HeadLayout: React.FC<HeadLayoutProps> = ({title, content, description}) => {
    if (!description) {
        description = "Дневник вопросов - приложение для отслеживания своих вопросов"
    }
    return (
        <NextHead>
            <title>{title}</title>
            <meta name="description" content={description} />
            <meta name="viewport" content="initial-scale=1.0, width=device-width" />
            <link rel="preconnect" href="https://fonts.googleapis.com" />
            <link rel="preconnect" href="https://fonts.gstatic.com" crossOrigin="true" />
            <link
                href="https://fonts.googleapis.com/css2?family=Ubuntu:wght@400;500;700&display=swap"
                rel="stylesheet"
            />
            {content}
        </NextHead>
    )
}

export default HeadLayout;