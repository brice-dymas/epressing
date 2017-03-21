(function() {
    'use strict';

    angular
        .module('epressingApp')
        .controller('TarifDeleteController',TarifDeleteController);

    TarifDeleteController.$inject = ['$uibModalInstance', 'entity', 'Tarif'];

    function TarifDeleteController($uibModalInstance, entity, Tarif) {
        var vm = this;

        vm.tarif = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Tarif.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
