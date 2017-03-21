(function() {
    'use strict';

    angular
        .module('epressingApp')
        .controller('UtilisateurDialogController', UtilisateurDialogController);

    UtilisateurDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'Utilisateur', 'User', 'TypeUtilisateur'];

    function UtilisateurDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, Utilisateur, User, TypeUtilisateur) {
        var vm = this;

        vm.utilisateur = entity;
        vm.clear = clear;
        vm.save = save;
        vm.users = User.query();
        vm.typeutilisateurs = TypeUtilisateur.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.utilisateur.id !== null) {
                Utilisateur.update(vm.utilisateur, onSaveSuccess, onSaveError);
            } else {
                Utilisateur.save(vm.utilisateur, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('epressingApp:utilisateurUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
