import { uniqueId } from 'lodash';

interface MenuitemsType {
    [x: string]: any;
    id?: string;
    navlabel?: boolean;
    subheader?: string;
    title?: string;
    icon?: any;
    href?: string;
    children?: MenuitemsType[];
    chip?: string;
    chipColor?: string;
    variant?: string;
    external?: boolean;
}

const Menuitems: MenuitemsType[] = [
    {
        id: uniqueId(),
        title: 'Личный кабинет',
        href: '/profile',
        chipColor: 'secondary',
    },
    {
        id: uniqueId(),
        title: 'Курсы',
        href: '/courses',
        chipColor: 'secondary',
    },
    {
        id: uniqueId(),
        title: 'Дисциплины',
        href: '/disciplines',
        chipColor: 'secondary',
    },
    {
        id: uniqueId(),
        title: 'Рейтинг',
        href: '/ratings',
        chipColor: 'secondary',
    },
    {
        id: uniqueId(),
        title: 'Поддержка',
        href: '/faq',
        chipColor: 'secondary',
    },
    // {
    //   id: uniqueId(),
    //   title: 'External Link',
    //   external: true,
    //   icon: IconStar,
    //   href: 'https://google.com',
    // },
];

export default Menuitems;
