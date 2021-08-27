import Head from 'next/head'

const HeadLayout = ({title, content, description}) => {
    if (!description) {
        description = "Дневник вопросов - приложение для отслеживания своих вопросов"
    }
    return (
        <Head>
            <title>{title}</title>
            <meta name="description" content={description} />
            <meta name="viewport" content="initial-scale=1.0, width=device-width" />
            <link rel="preconnect" href="https://fonts.googleapis.com" />
            <link rel="preconnect" href="https://fonts.gstatic.com" crossOrigin />
            <link
                href="https://fonts.googleapis.com/css2?family=Ubuntu:wght@400;500;700&display=swap"
                rel="stylesheet"
            />
            {content}
        </Head>
    )
}

export default HeadLayout;