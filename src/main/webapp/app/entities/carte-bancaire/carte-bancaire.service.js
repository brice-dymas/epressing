(function() {
    'use strict';
    angular
        .module('epressingApp')
        .factory('CarteBancaire', CarteBancaire);

    CarteBancaire.$inject = ['$resource', 'DateUtils'];

    function CarteBancaire ($resource, DateUtils) {
        var resourceUrl =  'api/carte-bancaires/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.dateExpiration = DateUtils.convertDateTimeFromServer(data.dateExpiration);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
