(function() {
    'use strict';

    angular
        .module('cercardiobitiApp')
        .controller('UserBDDialogController', UserBDDialogController);

    UserBDDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'UserBD'];

    function UserBDDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, UserBD) {
        var vm = this;

        vm.userBD = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.userBD.id !== null) {
                UserBD.update(vm.userBD, onSaveSuccess, onSaveError);
            } else {
                UserBD.save(vm.userBD, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('cercardiobitiApp:userBDUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.createdat = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
