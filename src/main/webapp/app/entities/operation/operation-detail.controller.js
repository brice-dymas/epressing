(function() {
    'use strict';

    angular
        .module('epressingApp')
        .controller('OperationDetailController', OperationDetailController);

    OperationDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Operation'];

    function OperationDetailController($scope, $rootScope, $stateParams, previousState, entity, Operation) {
        var vm = this;

        vm.operation = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('epressingApp:operationUpdate', function(event, result) {
            vm.operation = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
