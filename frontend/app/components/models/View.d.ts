interface ButtonProps {
    title: string
    loading: boolean
    onClick: (e: React.MouseEvent<HTMLElement>) => void
}

interface CheckboxProps {
    getRef: LegacyRef<HTMLLabelElement>
    dataId: string
    title: string
}

interface InputViewProps {
    icon: ReactElement | undefined
    onChange: (e: React.ChangeEvent<HTMLInputElement>) => void
    value: string
    password?: () => void
    showEye?: boolean | undefined
    getRef: LegacyRef<HTMLDivElement>
    dataId: string
    type: string
    placeholder: string
}