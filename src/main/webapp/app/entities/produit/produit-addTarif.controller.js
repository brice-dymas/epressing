(function() {
    'use strict';

    angular
        .module('epressingApp')
        .controller('ProduitAddTarifController', ProduitAddTarifController);

    ProduitAddTarifController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'DataUtils', 'entity', 'Tarif', 'Operation'];

    function ProduitAddTarifController ($timeout, $scope, $stateParams, $uibModalInstance, DataUtils, entity, Tarif, Operation) {
        var vm = this;

        vm.tarif = entity;
        console.log(entity.produit);
        vm.clear = clear;
        vm.save = save;
        vm.operations = Operation.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.tarif.id !== null) {
                Tarif.update(vm.tarif, onSaveSuccess, onSaveError);
            } else {
                Tarif.save(vm.tarif, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('epressingApp:tarifUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }
    }
})();
