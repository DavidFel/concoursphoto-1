(function() {
    'use strict';
    angular
        .module('concoursphotoApp')
        .factory('Theme', Theme);

    Theme.$inject = ['$resource'];

    function Theme ($resource) {
        var resourceUrl =  'api/themes/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
