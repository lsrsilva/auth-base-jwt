package dev.lsrdev.authbasejwt.commons.utils;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Utils {

    private Utils() {
    }

    public static <T extends Object> List<T> toList(Set<T> set) {
        List<T> list;
        if (set != null) {
            list = new ArrayList<>(set);
        } else {
            list = new ArrayList<>();
        }

        return list;
    }

    public static <T extends Object> Set<T> toSet(List<T> list) {
        Set<T> set;
        if (list != null) {
            set = new HashSet<>(list);
        } else {
            set = new HashSet<>();
        }

        return set;
    }

    public static String replaceAll(String valor, String regex, String replacement) {
        return valor.replaceAll(regex, replacement);
    }

    public static boolean isEmpty(Object o) {
        if (o == null) {
            return true;
        } else if (o instanceof String value) {
            return value.isEmpty();
        } else if (o instanceof Collection<?> value) {
            return value.isEmpty();
        } else if (o instanceof Map<?, ?> value) {
            return value.isEmpty();
        }
        return true;
    }

    public static Date toDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    public static PageRequest createPageRequest(Integer page, Integer size, String sortProperty, String sortDirection) {
        if (size == null) {
            size = 10;
        }

        if (page == null) {
            page = 0;
        }
        Sort.Direction direction = sortDirection != null && sortDirection.equalsIgnoreCase("asc") ?
                Sort.Direction.ASC : Sort.Direction.DESC;
        return PageRequest.of(page, size, direction, sortProperty);
    }

}
